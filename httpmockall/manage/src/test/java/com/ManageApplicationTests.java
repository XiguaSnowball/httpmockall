package com;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class ManageApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args){
		int num = 123;

		int rev = 0;
		while (num != 0) {
			rev = rev * 10 + num % 10;
			num /= 10;
		}




//		List a = Lists.newArrayList("a","b");
//		System.out.println(a);
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		if(n<2){
			System.out.println(0);
			System.out.println("\"从2开始计算质数\\n\"");
			return;
		}

		int i,j;
		for(i = n;i >= 2 ;i--){ //从n开始倒序遍历
			for(j = 2;j < i;j++){  //寻找质数
				if(i % j == 0)
					break;
			}
			if(i == j){   //找到倒序第一个质数，打印并跳出循环
				System.out.println(i);
				break;
			}
		}
	}
}
