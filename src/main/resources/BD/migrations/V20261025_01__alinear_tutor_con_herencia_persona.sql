-- Run once against an existing database after taking a backup.
-- The current application maps Tutor as a Persona subtype, so tutor.idpersona
-- must be its primary key and estudiante_tutor.idtutor stores that same ID.
-- Handles databases where tutor.idtutor was already removed by an earlier attempt.
SET @fk_tutor = (
    SELECT `CONSTRAINT_NAME`
    FROM `information_schema`.`KEY_COLUMN_USAGE`
    WHERE `TABLE_SCHEMA` = DATABASE()
      AND `TABLE_NAME` = 'estudiante_tutor'
      AND `COLUMN_NAME` = 'idtutor'
      AND `REFERENCED_TABLE_NAME` = 'tutor'
    LIMIT 1
);

SET @drop_fk_tutor = IF(
    @fk_tutor IS NULL,
    'SELECT 1',
    CONCAT(
        'ALTER TABLE `estudiante_tutor` DROP FOREIGN KEY `',
        REPLACE(@fk_tutor, '`', '``'),
        '`'
    )
);
PREPARE drop_fk_tutor_stmt FROM @drop_fk_tutor;
EXECUTE drop_fk_tutor_stmt;
DEALLOCATE PREPARE drop_fk_tutor_stmt;

SET @has_legacy_tutor_id = (
    SELECT COUNT(*)
    FROM `information_schema`.`COLUMNS`
    WHERE `TABLE_SCHEMA` = DATABASE()
      AND `TABLE_NAME` = 'tutor'
      AND `COLUMN_NAME` = 'idtutor'
);

SET @migrate_tutor_ids = IF(
    @has_legacy_tutor_id > 0,
    'UPDATE `estudiante_tutor` et JOIN `tutor` t ON t.`idtutor` = et.`idtutor` SET et.`idtutor` = t.`idpersona`',
    'SELECT 1'
);
PREPARE migrate_tutor_ids_stmt FROM @migrate_tutor_ids;
EXECUTE migrate_tutor_ids_stmt;
DEALLOCATE PREPARE migrate_tutor_ids_stmt;

SET @migrate_tutor_table = IF(
    @has_legacy_tutor_id > 0,
    'ALTER TABLE `tutor` DROP PRIMARY KEY, DROP COLUMN `idtutor`, ADD PRIMARY KEY (`idpersona`)',
    'SELECT 1'
);
PREPARE migrate_tutor_table_stmt FROM @migrate_tutor_table;
EXECUTE migrate_tutor_table_stmt;
DEALLOCATE PREPARE migrate_tutor_table_stmt;

SET @has_tutor_fk = (
    SELECT COUNT(*)
    FROM `information_schema`.`KEY_COLUMN_USAGE`
    WHERE `TABLE_SCHEMA` = DATABASE()
      AND `TABLE_NAME` = 'estudiante_tutor'
      AND `COLUMN_NAME` = 'idtutor'
      AND `REFERENCED_TABLE_NAME` = 'tutor'
      AND `REFERENCED_COLUMN_NAME` = 'idpersona'
);

SET @add_tutor_fk = IF(
    @has_tutor_fk = 0,
    'ALTER TABLE `estudiante_tutor` ADD CONSTRAINT `fk_et_tutor` FOREIGN KEY (`idtutor`) REFERENCES `tutor` (`idpersona`) ON DELETE RESTRICT ON UPDATE CASCADE',
    'SELECT 1'
);
PREPARE add_tutor_fk_stmt FROM @add_tutor_fk;
EXECUTE add_tutor_fk_stmt;
DEALLOCATE PREPARE add_tutor_fk_stmt;
