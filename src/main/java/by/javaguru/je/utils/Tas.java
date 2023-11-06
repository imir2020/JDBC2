package by.javaguru.je.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tas {
//    public static int[] twoSum(int[] nums, int target) {
//
//        int[] result = new int[2];
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = 1; j < nums.length ; j++) {
//                if(i!=j){
//                    result = target == nums[i] + nums[j] ? new int[]{i, j} : null;
//                    if (result != null) {
//                        break;
//
//                    }
//                }
//            }
//            if (result != null) {
//                break;
//
//            }
//        }
//        return result;
//    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> sym = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {

            int diff = target - nums[i];

            result = sym.containsKey(diff) ? new int[]{i, sym.get(diff)} : null;
            sym.put(nums[i], i);


            if (result != null) {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{3, 2, 4}, 6)));
    }
}
