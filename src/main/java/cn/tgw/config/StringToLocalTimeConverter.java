package cn.tgw.config;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

/*
 * @Project:tgw
 * @Description:converter
 * @Author:TjSanshao
 * @Create:2018-12-10 16:08
 *
 **/
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {

        String[] array = source.split(":");

        LocalTime time = LocalTime.of(Integer.valueOf(array[0]), Integer.valueOf(array[1]));

        if (time != null) {
            return time;
        }

        return null;
    }
}
