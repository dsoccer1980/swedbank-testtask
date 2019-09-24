package com.dsoccer1980.fuel.repository;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {

    List<FuelConsumption> findByDriverId(long id);

    @Query("SELECT new com.dsoccer1980.fuel.domain.dto.MoneyByMonth(MONTH(c.date), ROUND(sum(c.price*c.volume),2))" +
            " FROM FuelConsumption c GROUP BY MONTH(c.date)")
    List<MoneyByMonth> sumSpentMoneyGroupByMonth();

    @Query("SELECT c FROM FuelConsumption c WHERE MONTH(c.date)=:month")
    List<FuelConsumption> findFuelConsumptionByMonth(int month);

    @Query("SELECT new com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic(MONTH(c.date), c.fuelType, sum(c.volume)," +
            " avg(c.price), ROUND(sum(c.price*c.volume),2)) FROM FuelConsumption c GROUP BY MONTH(c.date), c.fuelType")
    List<FuelConsumptionStatistic> findFuelConsumptionGroupByFuelType();

    @Query("SELECT new com.dsoccer1980.fuel.domain.dto.MoneyByMonth(MONTH(c.date), ROUND(sum(c.price*c.volume),2))" +
            " FROM FuelConsumption c WHERE c.driverId=:driverId GROUP BY MONTH(c.date)")
    List<MoneyByMonth> sumSpentMoneyByDriverIdGroupByMonth(long driverId);

    @Query("SELECT c FROM FuelConsumption c WHERE MONTH(c.date)=:month AND c.driverId=:driverId")
    List<FuelConsumption> findFuelConsumptionByMonthAndByDriverId(int month, long driverId);

    @Query("SELECT new com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic(MONTH(c.date), c.fuelType, sum(c.volume)," +
            " avg(c.price), ROUND(sum(c.price*c.volume),2)) FROM FuelConsumption c WHERE c.driverId=:driverId GROUP BY MONTH(c.date), c.fuelType")
    List<FuelConsumptionStatistic> findFuelConsumptionByDriverIdGroupByFuelType(long driverId);
}
