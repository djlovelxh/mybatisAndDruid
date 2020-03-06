package dj.mybatis.config;

/**
 * Created by dj on 2020/2/23.
 */
public class DataSourceType {

    //内部枚举类，用于选择特定的数据类型
    public enum DataBaseType {
        primary, secondary
    }

    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<>();

    // 往当前线程里设置数据源类型
    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataBaseType);
    }

    // 获取数据源类型
    public static DataBaseType getDataBaseType() {
        return TYPE.get() == null ? DataBaseType.primary : TYPE.get();
    }

    // 清空数据类型
    public static void clearDataBaseType() {
        TYPE.remove();
    }
}
