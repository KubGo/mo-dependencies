within TestLibrary.HeatTransfer;

model InteriorConvectionTest
  BuildingsLite.HeatTransfer.Convection.Interior con annotation(
    Placement(transformation(extent = {{-20, -20}, {20, 20}})));
  BuildingsLite.HeatTransfer.Sources.PrescribedTemperature TA1 annotation(
    Placement(transformation(origin = {-20, -60}, extent = {{-60, 40}, {-40, 60}})));
  BuildingsLite.HeatTransfer.Sources.FixedTemperature TB2(T = 293.15) annotation(
    Placement(transformation(origin = {100, 20}, extent = {{-20, -40}, {-40, -20}}, rotation = -0)));
  Modelica.Blocks.Sources.Ramp ramp(height = 50, duration = 5, offset = 320, startTime = 100)  annotation(
    Placement(transformation(origin = {-110, -10}, extent = {{-10, -10}, {10, 10}})));
equation
  connect(TA1.port, con.solid) annotation(
    Line(points = {{-60, -10}, {-31, -10}, {-31, 0}, {-20, 0}}, color = {191, 0, 0}));
  connect(TB2.port, con.fluid) annotation(
    Line(points = {{60, -10}, {20, -10}, {20, 0}}, color = {191, 0, 0}));
  connect(ramp.y, TA1.T) annotation(
    Line(points = {{-98, -10}, {-82, -10}}, color = {0, 0, 127}));
end InteriorConvectionTest;
