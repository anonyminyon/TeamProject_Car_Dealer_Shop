package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EngineAndChassis {

    private Car carID;
    private float fuelTankCapacity;
    private String engineType;
    private String numberOfCylinder;
    private float cylinderCapacity;
    private String variableValveSystem;
    private String fuelSystem;
    private String maximumCapacity;
    private String maximumTorque;
    private String gear;

    public EngineAndChassis(float fuelTankCapacity, String engineType, String numberOfCylinder, float cylinderCapacity, String variableValveSystem, String fuelSystem, String maximumCapacity, String maximumTorque, String gear) {
        this.fuelTankCapacity = fuelTankCapacity;
        this.engineType = engineType;
        this.numberOfCylinder = numberOfCylinder;
        this.cylinderCapacity = cylinderCapacity;
        this.variableValveSystem = variableValveSystem;
        this.fuelSystem = fuelSystem;
        this.maximumCapacity = maximumCapacity;
        this.maximumTorque = maximumTorque;
        this.gear = gear;
    }

    
}
