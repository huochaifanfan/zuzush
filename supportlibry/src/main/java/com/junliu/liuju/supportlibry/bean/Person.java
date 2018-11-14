package com.junliu.liuju.supportlibry.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuju on 2018/4/10.
 */
@Entity
public class Person {
    /**
     * @Entity   标识实体类，greenDAO会映射成sqlite的一个表，表名为实体类名的大写形式
     * @Id 标识主键，该字段的类型为long或Long类型，autoincrement设置是否自动增长
     * @Property       标识该属性在表中对应的列名称, nameInDb设置名称
     * @Transient      标识该属性将不会映射到表中，也就是没有这列
     * @NotNull         设置表中当前列的值不可为空
     * @Convert         指定自定义类型(@linkPropertyConverter)
     * @Generated   greenDAO运行所产生的构造函数或者方法，被此标注的代码可以变更或者下次运行时清除
     * @Index    使用@Index作为一个属性来创建一个索引；定义多列索引(@link Entity#indexes())
     * @JoinEntity     定义表连接关系
     * @JoinProperty         定义名称和引用名称属性关系
     * @Keep     注解的代码段在GreenDao下次运行时保持不变
     * 1.注解实体类：默认禁止修改此类
    *2.注解其他代码段，默认禁止修改注解的代码段
     * @OrderBy        指定排序
     * @ToMany         定义与多个实体对象的关系
     * @ToOne  定义与另一个实体（一个实体对象）的关系
     * @Unique 向数据库列添加了一个唯一的约束
     *
     *
     * 定义好了之后按ctrl+F9（或者工具栏build下面的Make Project）对项目进行重新构建，再
    *daoPackage的目录下就会自动生成代码
     */
    @Id(autoincrement = true)
    private long id;
    @Property(nameInDb = "UserName")
    private String name;
    private int age;
    @Generated(hash = 1847003947)
    public Person(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
