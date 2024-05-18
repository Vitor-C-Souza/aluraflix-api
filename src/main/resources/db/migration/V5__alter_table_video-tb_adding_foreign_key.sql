ALTER TABLE `aluraflix_db`.`videos_tb`
ADD COLUMN `categoria_id` BIGINT NOT NULL AFTER `url`,
ADD INDEX `categoriaId_idx` (`categoria_id` ASC) VISIBLE;
;
ALTER TABLE `aluraflix_db`.`videos_tb`
ADD CONSTRAINT `categoria_id`
  FOREIGN KEY (`categoria_id`)
  REFERENCES `aluraflix_db`.`categorias_tb` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;