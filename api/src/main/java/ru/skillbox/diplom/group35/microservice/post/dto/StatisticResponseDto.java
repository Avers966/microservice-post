package ru.skillbox.diplom.group35.microservice.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.statistic.StatisticPerDateDto;

import java.util.List;

/**
 * PostStatisticResponseDto
 *
 * @author Vladimir Polochanin
 */

@Getter
@Setter
@NoArgsConstructor
public class StatisticResponseDto extends StatisticPerDateDto {
    private List<StatisticPerDateDto> countPerHours;
    private List<StatisticPerDateDto> countPerMonth;
}

