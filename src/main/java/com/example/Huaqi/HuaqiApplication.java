package com.example.Huaqi;

import com.example.Huaqi.bl.OptionService;
import com.example.Huaqi.blImpl.OptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class HuaqiApplication {

//	private static Timer timer = new Timer();//创建一个Timer类的实例

	public static void main(String[] args) {
		SpringApplication.run(HuaqiApplication.class, args);
	}
}
