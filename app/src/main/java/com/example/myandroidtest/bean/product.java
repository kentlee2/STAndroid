package com.example.myandroidtest.bean;

/**
 * 工厂模式
 */
// 定义产品接口
interface Product {
    void operation();
}

// 具体产品类A
class ConcreteProductA implements Product {
    @Override
    public void operation() {
        System.out.println("This is Product A.");
    }
}

// 具体产品类B
class ConcreteProductB implements Product {
    @Override
    public void operation() {
        System.out.println("This is Product B.");
    }
}

// 工厂类
class Factory {
    public static Product createProduct(String type) {
        if (type.equalsIgnoreCase("A")) {
            return new ConcreteProductA();
        } else if (type.equalsIgnoreCase("B")) {
            return new ConcreteProductB();
        }
        return null;
    }
}

 class Main {
    public static void main(String[] args) {
        // 创建具体产品A
        Product productA = Factory.createProduct("A");
        productA.operation();

        // 创建具体产品B
        Product productB = Factory.createProduct("B");
        productB.operation();
    }
}

