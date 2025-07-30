package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,
        //добавил параметры
        @Schema(description = "Длинна упаковки, милиметры", example = "length: 345")
        BigInteger length,
        @Schema(description = "Ширина упаковки, милиметры", example = "width: 589")
        BigInteger width,
        @Schema(description = "Высота упаковки, милиметры", example = "height: 234")
        BigInteger height

) {

}
