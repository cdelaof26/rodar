INSERT INTO `status` (`id`,`name`) VALUES
(1,'pending_payment'),
(2,'confirmed'),
(3,'completed');

INSERT INTO `payment_method` (`id`,`name`) VALUES
(1,'cash'),
(2,'debit_card'),
(3,'credit_card');

INSERT INTO `payment` (`id`,`reservation_id`,`user_id`,`amount`,`status`) VALUES
(1,1,1,200.00,1),
(2,2,2,3000.00,1),
(3,3,3,500.00,1),
(4,4,3,300.00,1),
(5,5,2,700.00,1);

INSERT INTO `payment` (`id`,`reservation_id`,`user_id`,`amount`,`payment_method_id`,`status`) VALUES
(6,6,4,340.00,2,3),
(7,7,1,650.00,3,3),
(8,8,5,120.00,1,3),
(9,9,3,333.00,2,3),
(10,10,5,910.00,3,3);
