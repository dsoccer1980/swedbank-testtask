package com.dsoccer1980.fuel.repository;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.MoneyByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {

    List<FuelConsumption> findByDriverId(long id);

    @Query("SELECT new com.dsoccer1980.fuel.domain.MoneyByMonth(MONTH(c.date), sum(c.price*c.volume)) FROM FuelConsumption c GROUP BY MONTH(c.date)")
    List<MoneyByMonth> sumSpentMoneyGroupByMonth();

    @Query("SELECT c FROM FuelConsumption c WHERE MONTH(c.date)=:month")
    List<FuelConsumption> findFuelConsumptionByMonth(int month);

    @Query("SELECT new com.dsoccer1980.fuel.domain.FuelConsumptionStatistic(MONTH(c.date), c.fuelType, sum(c.volume)," +
            " avg(c.price), sum(c.price*c.volume)) FROM FuelConsumption c GROUP BY MONTH(c.date)")
    List<FuelConsumptionStatistic> findFuelConsumptionGroupByFuelType();
}
