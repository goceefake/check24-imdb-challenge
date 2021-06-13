CREATE TABLE IF NOT EXISTS `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `movie_rate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rate` int NOT NULL,
  `movie_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7otfnajcc767xr87eabr6f65` (`movie_id`),
  KEY `FKhs8i272f27tbgigxom5yi236v` (`user_id`),
  CONSTRAINT `FKhs8i272f27tbgigxom5yi236v` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`),
  CONSTRAINT `FKi7otfnajcc767xr87eabr6f65` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
);

INSERT INTO check24imdb.user_entity (username, password)
SELECT * FROM (SELECT 'cem', '$2a$10$v2Ntvegq014IkIowoilCK.OWyR6Jz.GdiIHKKP9mdXKDs8A2MW/de') AS tmp
WHERE NOT EXISTS (SELECT username FROM check24imdb.user_entity WHERE username = 'cem');

INSERT INTO check24imdb.movie (name)
SELECT * FROM (SELECT 'Tangerines') AS tmp
WHERE NOT EXISTS (SELECT name FROM check24imdb.movie WHERE name = 'Tangerines');

INSERT INTO check24imdb.movie_rate (rate,movie_id,user_id)
SELECT * FROM (SELECT 4 as rate,1 as movie_id, 1 as user_id ) AS tmp
WHERE NOT EXISTS (SELECT rate,movie_id,user_id FROM check24imdb.movie_rate WHERE movie_id = 1 and user_id = 1);

commit;