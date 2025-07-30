package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimensions.CargoVolumeImpl;
import ru.fastdelivery.domain.common.dimensions.TotalVolume;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.pack.PackOfVolume;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.domain.delivery.shipment.ShipmentOfVolume;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        var packsWeights = request.packages().stream()
                .map(CargoPackage::weight)
                .map(Weight::new)
                .map(Pack::new)
                .toList();

        var shipment = new Shipment(packsWeights, currencyFactory.create(request.currencyCode()));
        var calculatedPrice = tariffCalculateUseCase.calc(shipment);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        //попытка расчета по размерам
        List<PackOfVolume> packOfVolumeList=new ArrayList<>();
        for (CargoPackage cargoPackage:request.packages()){
            CargoVolumeImpl cargoVolumeImpl=new CargoVolumeImpl(cargoPackage.length(),
                    cargoPackage.width(),cargoPackage.height());
            packOfVolumeList.add(new PackOfVolume(new TotalVolume(cargoVolumeImpl.getVolumeOfOnePackage())));
        }
        var shipmentOfVolume = new ShipmentOfVolume(packOfVolumeList, currencyFactory
                .create(request.currencyCode()));
        var calculatedPriceOfVolume = tariffCalculateUseCase.calcVolume(shipmentOfVolume);
        Price maxPrice = null;
        if (calculatedPrice.amount().compareTo(calculatedPriceOfVolume.amount())>=0){
            maxPrice = calculatedPrice;
        }else {
            maxPrice=calculatedPriceOfVolume;
        }

        return new CalculatePackagesResponse(maxPrice, minimalPrice);
    }
}

