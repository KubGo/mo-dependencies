within BuildingsLite.Tests;

model SimpleModel
  HeatTransfer.Conduction.SingleLayer layer(A = 1, redeclare BuildingsLite.HeatTransfer.Data.Solids.Concrete material)  annotation(
    Placement(transformation(origin = {8, 8}, extent = {{-10, -10}, {10, 10}})));
  Controls.Discrete.BooleanDelay booleanDelay(samplePeriod = 1)  annotation(
    Placement(transformation(origin = {-70, 50}, extent = {{-10, -10}, {10, 10}})));
  Airflow.Multizone.MediumColumn col(densitySelection = BuildingsLite.Airflow.Multizone.Types.densitySelection.fromTop)  annotation(
    Placement(transformation(origin = {50, 50}, extent = {{-10, -10}, {10, 10}})));
  BouncingBall bouncingBall;
  OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction conductionModel;
equation

end SimpleModel;
