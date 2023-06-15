package ru.skillbox.diplom.group35.microservice.post.dto.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Статистика по постам, комментариям и лайкам за данную дату")
public class StatisticPerDate {

    @Schema(description = "Дата для статистики")
    Date date;

    @Schema(description = "Количество или постов, или комментариев, или лайков")
    Integer count;
}

