package com.garlane.relation.common.utils.change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class CollectionUtil {

    /**
     * 拆分集合
     * @param list 要拆分的集合
     * @param count 每个数组的元素个数,最后一个数组的元素个数可能小于count
     * @return 返回拆分后的各个数组
     */
    public static Collection<String[]> split(Collection<String> list, int count) {
        Collection<String[]> result = new ArrayList<String[]>();

        if (list != null && list.size() > 0 && count > 0) {
            int size = list.size();

            String[] values = new String[size];
            list.toArray(values);
            if (size <= count) {
                result.add(values);
            } else {
                int srcPos = 0;
                int destPos = 0;
                int destLength = count;
                boolean flag = true;
                while (flag) {
                    String[] dest = new String[destLength];
                    System.arraycopy(values, srcPos, dest, destPos, destLength);
                    result.add(dest);

                    srcPos = srcPos + destLength;
                    if (srcPos == size) {
                        flag = false;
                    } else if ((srcPos + count) > size) {
                        destLength = size - srcPos;
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * asSet:(根据list集合产生一个set). <br/> 
     * @author liyhu
     * @date 2017年12月5日上午9:40:30
     * @param list
     * @return
     */
    public static <T> Set<T> asSet(List<T> list) {
        return new HashSet<T>(list);
    }
    
    /**
     * splitCollection:(把一个大的集合拆成若干个指定大小的集合). <br/> 
     * @author linzj
     * @date 2017年5月27日上午9:03:41
     * @param values
     * @param size
     * @return List<Collection<String>>
     */
    public static List<Collection<String>> splitCollection(Collection<String> values, int size) {
        List<Collection<String>> result = new ArrayList<Collection<String>>();
        if (values.size() <= size) {
            result.add(values);
        } else {
            Collection<String> subValues  = null;
            int count = 0;
            for (String value : values) {
                if (subValues == null) {
                    subValues = new ArrayList<String>();
                    result.add(subValues);
                }
                
                subValues.add(value);
                count ++;
                
                if (count == size) {
                    count = 0;
                    subValues = null;
                }
            }
        }
        return result;
    }

    /**
     * 
     * getFirst:(获取集合的第一个元素). <br/>
     * @author wengsw
     * @date 2017年4月18日下午3:16:52
     * @param list
     * @return 返回第一个值
     */
    public static <T> T getFirst(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
    
}
