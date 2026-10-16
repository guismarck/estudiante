-- Run once against the existing database after taking a backup.
-- Existing rows keep idSalon = NULL until each detail is assigned to its salon.
ALTER TABLE `detalle_plan_de_estudio`
    ADD COLUMN `idSalon` INT UNSIGNED NULL AFTER `idDocente`,
    ADD KEY `idx_detalle_plan_salon` (`idSalon`),
    ADD CONSTRAINT `fk_det_salon`
        FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    DROP INDEX `uk_plan_asignatura`,
    ADD UNIQUE KEY `uk_plan_asignatura_salon`
        (`idPlan_de_estudio`, `idAsignatura`, `idSalon`);

-- Assign existing detail rows explicitly after reviewing the plan, subject,
-- teacher and salon. Example:
-- UPDATE `detalle_plan_de_estudio`
-- SET `idSalon` = <idSalon>
-- WHERE `iddetalle_plan_de_estudio` = <idDetalle>;
