-- H2 数据库初始化数据脚本

-- 插入管理员账号（密码：123456，MD5加密后）
INSERT INTO user (username, password, phone, nickname, role) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '13800000000', '管理员', 'admin');

-- 插入测试用户（密码：123456）
INSERT INTO user (username, password, phone, nickname, role) VALUES
('user1', 'e10adc3949ba59abbe56e057f20f883e', '13800000001', '测试用户1', 'user'),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '13800000002', '测试用户2', 'user');

-- 插入商品分类
INSERT INTO category (id, name, description, parent_id, sort_order) VALUES
(1, '水果生鲜', '新鲜水果蔬菜', 0, 1),
(2, '数码电器', '数码产品和家用电器', 0, 2),
(3, '服装鞋包', '服装鞋类箱包', 0, 3),
(4, '食品饮料', '各类食品饮料', 0, 4),
(5, '图书文具', '图书和文具用品', 0, 5);

-- 插入测试商品（带图片）
INSERT INTO product (name, description, price, stock, image_url, category_id, status) VALUES
('新鲜苹果', '产地直供，新鲜美味', 9.9, 100, 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300', 1, 1),
('香蕉', '进口香蕉，香甜可口', 6.8, 150, 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=300', 1, 1),
('橙子', '新鲜橙子，维C丰富', 8.5, 120, 'https://images.unsplash.com/photo-1582979512210-99b6a53386f9?w=300', 1, 1),
('iPhone 14', '苹果最新款手机', 5999.00, 50, 'https://images.unsplash.com/photo-1591337676887-a217a6970a8a?w=300', 2, 1),
('小米13', '小米旗舰手机', 3999.00, 80, 'https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=300', 2, 1),
('华为MateBook', '华为笔记本电脑', 6999.00, 30, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300', 2, 1),
('男士T恤', '纯棉舒适T恤', 89.00, 200, 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300', 3, 1),
('运动鞋', '透气运动鞋', 299.00, 100, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300', 3, 1),
('背包', '时尚双肩包', 159.00, 150, 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=300', 3, 1),
('矿泉水', '天然矿泉水500ml', 2.5, 500, 'https://images.unsplash.com/photo-1523362628745-0c100150b504?w=300', 4, 1),
('可乐', '可口可乐330ml', 3.0, 300, 'https://images.unsplash.com/photo-1554866585-cd94860890b7?w=300', 4, 1),
('薯片', '香脆可口薯片', 8.9, 200, 'https://images.unsplash.com/photo-1600952841320-db92ec4047ca?w=300', 4, 1),
('编程书籍', 'Java从入门到精通', 59.00, 80, 'https://epaper.gmw.cn/zhdsb/images/2018-10/10/17/res04_attpic_brief.jpg', 5, 1),
('笔记本', 'A5记事本', 12.00, 300, 'https://images.unsplash.com/photo-1531346878377-a5be20888e57?w=300', 5, 1),
('中性笔', '0.5mm中性笔', 2.5, 500, 'https://p0.ssl.qhimgs1.com/t043deb239f0b2dea56.jpg', 5, 1);

-- 插入测试地址
INSERT INTO address (user_id, receiver, phone, province, city, district, detail, is_default) VALUES
(2, '张三', '13800000001', '北京市', '朝阳区', '建国门街道', '建国路1号', TRUE),
(2, '李四', '13800000001', '北京市', '海淀区', '中关村街道', '中关村大街2号', FALSE);
