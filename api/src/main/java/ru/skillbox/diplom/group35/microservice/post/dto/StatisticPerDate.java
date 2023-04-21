package ru.skillbox.diplom.group35.microservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * StatPerMonth
 *
 * @author Vladimir Polochanin
 */

@Data
@AllArgsConstructor
public class StatisticPerDate {
    Date date;
    Integer count;
}

