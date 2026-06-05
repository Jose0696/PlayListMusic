USE playlist_songs;

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_CreatePlayList $$
CREATE PROCEDURE sp_CreatePlayList(
    IN p_NamePlayList VARCHAR(100),
    IN p_Information VARCHAR(255)
)
BEGIN
    INSERT INTO PlayList (
        NamePlayList,
        Information
    )
    VALUES (
        p_NamePlayList,
        p_Information
    );

    SELECT 
        IdPlayList,
        NamePlayList,
        Information,
        DateCreate
    FROM PlayList
    WHERE IdPlayList = LAST_INSERT_ID();
END $$


DROP PROCEDURE IF EXISTS sp_UpdatePlayList $$
CREATE PROCEDURE sp_UpdatePlayList(
    IN p_IdPlayList INT,
    IN p_NamePlayList VARCHAR(100),
    IN p_Information VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM PlayList
        WHERE IdPlayList = p_IdPlayList
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The playlist does not exist';
    END IF;

    UPDATE PlayList
    SET 
        NamePlayList = p_NamePlayList,
        Information = p_Information
    WHERE IdPlayList = p_IdPlayList;

    SELECT 
        IdPlayList,
        NamePlayList,
        Information,
        DateCreate
    FROM PlayList
    WHERE IdPlayList = p_IdPlayList;
END $$


DROP PROCEDURE IF EXISTS sp_DeletePlayList $$
CREATE PROCEDURE sp_DeletePlayList(
    IN p_IdPlayList INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error deleting playlist';
    END;

    IF NOT EXISTS (
        SELECT 1
        FROM PlayList
        WHERE IdPlayList = p_IdPlayList
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The playlist does not exist';
    END IF;

    START TRANSACTION;

    DELETE FROM PlayListSongs
    WHERE IdPlayList = p_IdPlayList;

    DELETE FROM PlayList
    WHERE IdPlayList = p_IdPlayList;

    COMMIT;

    SELECT 'PlayList deleted successfully' AS Message;
END $$


DROP PROCEDURE IF EXISTS sp_GetPlayLists $$
CREATE PROCEDURE sp_GetPlayLists()
BEGIN
    SELECT 
        IdPlayList,
        NamePlayList,
        Information,
        DateCreate
    FROM PlayList
    ORDER BY DateCreate DESC;
END $$


DROP PROCEDURE IF EXISTS sp_AddSongToPlayList $$
CREATE PROCEDURE sp_AddSongToPlayList(
    IN p_IdPlayList INT,
    IN p_IdSong INT
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 
        FROM PlayList 
        WHERE IdPlayList = p_IdPlayList
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The playlist does not exist';
    END IF;

    IF NOT EXISTS (
        SELECT 1 
        FROM Songs 
        WHERE IdSong = p_IdSong
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The song does not exist';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM PlayListSongs
        WHERE IdPlayList = p_IdPlayList
          AND IdSong = p_IdSong
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The song is already assigned to this playlist';
    END IF;

    INSERT INTO PlayListSongs (
        IdPlayList,
        IdSong
    )
    VALUES (
        p_IdPlayList,
        p_IdSong
    );

    SELECT 
        pls.IdPlayListSong,
        pls.IdPlayList,
        pls.IdSong,
        s.Title,
        s.Artist,
        s.Album,
        s.Genre,
        s.LapseTime,
        pls.AssignmentDate
    FROM PlayListSongs pls
    INNER JOIN Songs s ON s.IdSong = pls.IdSong
    WHERE pls.IdPlayListSong = LAST_INSERT_ID();
END $$


DROP PROCEDURE IF EXISTS sp_RemoveSongFromPlayList $$
CREATE PROCEDURE sp_RemoveSongFromPlayList(
    IN p_IdPlayList INT,
    IN p_IdSong INT
)
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM PlayListSongs
        WHERE IdPlayList = p_IdPlayList
          AND IdSong = p_IdSong
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The song is not assigned to this playlist';
    END IF;

    DELETE FROM PlayListSongs
    WHERE IdPlayList = p_IdPlayList
      AND IdSong = p_IdSong;

    SELECT 'Song removed from playlist successfully' AS Message;
END $$


DROP PROCEDURE IF EXISTS sp_GetSongsByPlayList $$
CREATE PROCEDURE sp_GetSongsByPlayList(
    IN p_IdPlayList INT
)
BEGIN
    SELECT 
        s.IdSong,
        s.Title,
        s.Artist,
        s.Album,
        s.Genre,
        s.LapseTime,
        pls.AssignmentDate
    FROM PlayListSongs pls
    INNER JOIN Songs s ON s.IdSong = pls.IdSong
    WHERE pls.IdPlayList = p_IdPlayList
    ORDER BY pls.AssignmentDate DESC;
END $$


DROP PROCEDURE IF EXISTS sp_InsertSongCatalog $$
CREATE PROCEDURE sp_InsertSongCatalog(
    IN p_Title VARCHAR(150),
    IN p_Artist VARCHAR(150),
    IN p_Album VARCHAR(150),
    IN p_Genre VARCHAR(100),
    IN p_LapseTime VARCHAR(10)
)
BEGIN
    INSERT INTO Songs (
        Title,
        Artist,
        Album,
        Genre,
        LapseTime
    )
    VALUES (
        p_Title,
        p_Artist,
        p_Album,
        p_Genre,
        p_LapseTime
    );

    SELECT 
        IdSong,
        Title,
        Artist,
        Album,
        Genre,
        LapseTime
    FROM Songs
    WHERE IdSong = LAST_INSERT_ID();
END $$


DROP PROCEDURE IF EXISTS sp_GetSongsCatalog $$
CREATE PROCEDURE sp_GetSongsCatalog()
BEGIN
    SELECT 
        IdSong,
        Title,
        Artist,
        Album,
        Genre,
        LapseTime
    FROM Songs
    ORDER BY Title ASC;
END $$

DELIMITER ;
