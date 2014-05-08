package com.disankyo.sort;

/**
 * 
 * @version 1.00 2012-5-14 11:28:57
 * @since 1.6
 * @author  
 */
public class SortUtil {

    /**
     * 冒泡排序:将相邻两数进行比较,完成一次循环,最大数移动到末尾(升序排列的情况)
     *        以后每次比较次数较上次减1
     * @param data 待排序的数组
     * @return 排序后的数组
     */
    public static int[] bubbleSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data.length - i; j++) {
                if (data[j] > data[j + 1]) {
                    swap(data, j, j + 1);
                }
            }
        }

        return data;
    }

    /**
     * 选择排序：每一轮循环最大的数会移动到末尾(升序排列的情况),以后每次比较次数较上一次减1
     * @param data 待排序的数组
     * @return 排序后的数组
     */
    public static int[] selectSort(int[] data) {
        int index;
        for (int i = 1; i < data.length; i++) {
            index = 0;

            for (int j = 1; j <= data.length - i; j++) {
                if (data[j] > data[index]) {
                    index = j;
                }
            }

            swap(data, data.length - i, index);
        }

        return data;
    }

    /**
     * 插入排序(升序排列):每完成一次循环,都会实现i之前的排序
     * @param data 待排序的数组
     * @return 排序后的数组
     */
    public static int[] insertSort(int[] data) {
        int temp;
        for (int i = 1; i < data.length; i++) {
            int point = i;//i之前的数据都已实现排序
            temp = data[i];//要实现插入的数据
            while (point > 0 && data[point - 1] > temp) {//选择一个不大于插入数据的位置
                data[point] = data[point - 1];//将数据向后移
                point--;
            }

            data[point] = temp;//将要插入的数据插入合适的位置
        }
        return data;
    }

    /**
     * 快速排序(升序为例):把数组由设定的参考值为界，分成两个数组，小于该数的在左侧，大于该数的
     *    在右侧，然后再分别对左侧、右侧的数组进行递归快速排序
     * @param data 待排序的数组
     * @return 排序后的数组
     */
    public static int[] quickSort(int[] data) {
        quick(data, 0, data.length - 1);

        return data;
    }

    private static int[] quick(int[] data, int low, int high) {
        int i, j, temp;
        if (low < high) {
            i = low;
            j = high;
            temp = data[i];//把第一个数作为比较的标准
            while (i < j) {//结束递归的条件

                //从右查找,找出比参数值小的数
                while (i < j && data[j] > temp) {
                    j--;
                }
                if (i < j) {
                    data[i] = data[j];//把小于该参考值的数赋值当前位置
                    i++;
                }

                //从左查找,找出比参数值大的数
                while (i < j && data[i] < temp) {
                    i++;
                }
                if (i < j) {
                    data[j] = data[i];//把
                    j--;
                }

            }

            data[i] = temp;
            quick(data, low, i - 1);
            quick(data, i + 1, high);
        }

        return data;
    }

    /**
     * 二分排序算法
     * @param data 待排序数组
     * @return  排序后的数组
     */
    public static int[] binarySort(int data[]) {
        int i, j, k, key;
        for (i = 1; i < data.length; i++) {
            key = data[i];
            if (data[i] < data[0]) {
                k = 0;
            } else {
                k = binarySearch(data, 0, i, key);
            }

            for (j = i; j > k; j--) {
                data[j] = data[j - 1];
            }
            data[k] = key;
        }

        return data;
    }

    /**
     * 归并排序
     * @param data 待排序的数组
     * @param start 
     * @param end
     * @return
     */
    public static int[] mergeSort(int data[], int start, int end) {
        if (start < end) {
            int i;
            i = (end + start) / 2;
            // 对前半部分进行排序
            mergeSort(data, start, i);
            // 对后半部分进行排序
            mergeSort(data, i + 1, end);
            // 合并前后两部分
            merge(data, start, i, end);
        }

        return data;
    }

    /**
     * 归并排序中的合并算法
     * @param data 原数组
     * @param start 数组的开始索引
     * @param mid 中间位置索引
     * @param end 结束位置的索引
     */
    private static void merge(int data[], int start, int mid, int end) {
        int[] temp1 = new int[mid - start + 2];
        int[] temp2 = new int[end - mid + 1];
        int n1, n2;
        n1 = mid - start + 1;
        n2 = end - mid;

        // 拷贝前半部分数组
        for (int i = 0; i < n1; i++) {
            temp1[i] = data[start + i];
        }
        // 拷贝后半部分数组
        for (int i = 0; i < n2; i++) {
            temp2[i] = data[mid + i + 1];
        }
        // 把后面的元素设置的很大
        temp1[n1] = temp2[n2] = 1000;
        // 逐个扫描两部分数组然后放到相应的位置去
        for (int k = start, i = 0, j = 0; k <= end; k++) {
            if (temp1[i] <= temp2[j]) {
                data[k] = temp1[i];
                i++;
            } else {
                data[k] = temp2[j];
                j++;
            }
        }
    }

    /**
     * 二分排序中使用的二分查找算法:地位到高位之间是已经排序的数据
     * @param data 数组
     * @param low 地位索引
     * @param high 高位索引
     * @param key 要查找的数
     * @return 该数在low~high中的位置索引
     */
    private static int binarySearch(int data[], int low, int high, int key) {
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (data[mid] < key && key <= data[mid + 1]) {
                return (mid + 1);
            } else if (data[mid] < key) {
                low = mid + 1;
            } else {
                if (data[mid] > key) {
                    high = mid - 1;
                } else {
                    return mid;
                }
            }
        }
        return high;
    }

    /**
     * 二分查找算法
     * @param data 目标数组
     * @param seek 要查找的数据
     * @return 目标数据在数组中的位置索引
     */
//    public static int binarySearch(int[] data, int start, int end, int seek) {
//        int index = -1;
//        int mid = 0;
//        while (start <= end) {
//            mid = (start + end) / 2;
//            if (data[mid] == seek) {
//                index = mid;
//            }
//            if (data[mid] < seek) {
//                start = mid + 1;
//            } else {
//                end = mid - 1;
//            }
//        }
//
//        return index;
//    }
    /**
     * 交换相邻的两个数
     * @param data 排序的数组
     * @param x 下标
     * @param y 下标
     */
    private static void swap(int[] data, int x, int y) {
        int temp = data[x];
        data[x] = data[y];
        data[y] = temp;
    }

    public static void main(String[] args) {
        int[] data = {3, 2, 4, 5, 7, 5, 6};
//        bubbleSort(ss);
//        selectSort(ss);
//        insertSort(ss);
//        quickSort(data);
//        binarySort(data);
//        mergeSort(data, 0, 6);

        for (int d : data) {
            System.out.println("========" + d);
        }

        System.out.println("=====11========="+binarySearch(data, 0, 3, 5));

    }
}
