-- 创建数据库
CREATE DATABASE IF NOT EXISTS shopping_mall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_mall;

-- 删除已存在的表（开发环境使用，生产环境请谨慎）
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

-- 用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    email VARCHAR(50) COMMENT '邮箱',
    nickname VARCHAR(50) COMMENT '昵称',
    role VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：user-用户, admin-管理员',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_username (username),
    INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品分类表
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
    description VARCHAR(200) COMMENT '分类描述',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID，0表示顶级分类',
    sort_order INT DEFAULT 0 COMMENT '排序号',
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    description VARCHAR(500) COMMENT '商品描述',
    price DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存',
    image_url VARCHAR(200) COMMENT '商品图片URL',
    category_id BIGINT COMMENT '分类ID',
    status INT NOT NULL DEFAULT 1 COMMENT '商品状态：0-下架, 1-上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 购物车项表
CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT NOT NULL COMMENT '购物车ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    product_name VARCHAR(100) COMMENT '商品快照-名称',
    product_price DECIMAL(10, 2) COMMENT '商品快照-价格',
    product_image VARCHAR(200) COMMENT '商品快照-图片',
    checked BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否选中',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id (product_id),
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车项表';

-- 收货地址表
CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    receiver VARCHAR(50) NOT NULL COMMENT '收件人',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    province VARCHAR(50) NOT NULL COMMENT '省份',
    city VARCHAR(50) NOT NULL COMMENT '城市',
    district VARCHAR(50) NOT NULL COMMENT '区/县',
    detail VARCHAR(200) NOT NULL COMMENT '详细地址',
    is_default BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否为默认地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- 订单表
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
    status INT NOT NULL DEFAULT 0 COMMENT '订单状态：0-待支付, 1-已支付, 2-已发货, 3-已完成, 4-已取消',
    receiver VARCHAR(50) COMMENT '收货人',
    phone VARCHAR(20) COMMENT '联系电话',
    address VARCHAR(300) COMMENT '收货地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    pay_time DATETIME COMMENT '支付时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单项表
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    product_image VARCHAR(200) COMMENT '商品图片',
    product_price DECIMAL(10, 2) NOT NULL COMMENT '商品单价',
    quantity INT NOT NULL COMMENT '购买数量',
    total_price DECIMAL(10, 2) NOT NULL COMMENT '小计',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- 插入初始数据

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

-- 插入测试商品
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
