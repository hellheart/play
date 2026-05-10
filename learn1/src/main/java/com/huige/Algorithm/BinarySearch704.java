package com.huige.Algorithm;

import java.util.Arrays;

public class BinarySearch704 {
    /**
     * 二分查找
     * @param nums
     * @param target
     * @return int
     */
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if(nums[mid] < target) {
                left = mid + 1;
            } else if(nums[mid] > target) {
                right = mid -1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1,0,3,5,9,12};
        int target = 9;
        int search = search(nums, target);
        System.out.println(search);
    }
}
