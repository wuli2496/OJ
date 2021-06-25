INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(1,'good','Manager1',10,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(2,'very good','Manager2',9,'John_Doe','2021-02-20 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(3,'good','Manager3',7,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(4,'not so good','Manager4',5,'John_Doe','2021-02-21 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(5,'good','Manager5',7,'John_Doe','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(6,'very good','Manager1',8,'User2','2021-02-20 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(7,'old school','Manager1',6,'User3','2021-02-24 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(8,'not so good','Manager1',3,'User4','2021-02-21 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(9,'OK OK','Manager1',5,'User5','2021-02-23 15:07:16.643357');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_ratings`(`id`,`feedback`,`manager_name`,`rating`,`user_name`,`created_date`) VALUES(10,'Average','Manager1',5,'User6','2021-02-24 15:07:16.643357');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (1, 32400, 36000, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (2, 36000, 37800, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (3, 37800, 39600, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (4, 43200, 46800, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (5, 46800, 50400, 'Manager1');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (6, 32400, 36000, 'Manager2');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (7, 36000, 37800, 'Manager2');
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_slots`(`id`,`start_time`,`end_time`, `manager_name`) VALUES (8, 37800, 43200, 'Manager2');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (1, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (2, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (3, 'some address', '2021-03-06 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (4, 'some address', '2021-03-07 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (5, 'some address', '2021-03-08 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');


INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (6, 'some address', '2021-03-09 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (7, 'some address', '2021-03-10 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'KT', 'John_Doe');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointments` (`id`, `address`, `appointment_date`, `cancelation_reason`, `description`, `manager_name`, `mode`, `new_date`, `status`, `subject`, `user_name`)
VALUES (8, 'some address', '2021-03-05 09:00:00.000000', NULL, 'desc', 'Manager1', 'Video', NULL, 'Pending', 'some subject', 'Other User');

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (1, 1, 1);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (2, 2, 1);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (3, 1, 2);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (4, 2, 2);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (5, 1, 3);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (6, 2, 3);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (7, 1, 4);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (8, 2, 4);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (9, 1, 5);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (10, 2, 5);


INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (11, 1, 6);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (12, 2, 6);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (13, 1, 7);
INSERT INTO `RM_APPOINTMENT_SERVICE`.`manager_appointments` (`id`,`manager_slot_id`,`appointments_id`) VALUES (14, 2, 7);

INSERT INTO `RM_APPOINTMENT_SERVICE`.`appointment_attachments` (`id`, `created_date`, `file_name`, `url`, `appointment_id`) VALUES (1, '2021-02-25 11:22:12.133237', 'dummyfile', 'dummyUrl', 8);
