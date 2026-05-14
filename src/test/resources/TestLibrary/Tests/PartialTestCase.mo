within TestLibrary.Tests;

partial model PartialTestCase
  BuildingsLite.HeatTransfer.Sources.FixedTemperature T2(T = 303.15) "Boundary condition for surface 2" annotation(
    Placement(transformation(origin = {20, -20}, extent = {{20, -20}, {40, 0}})));
  BuildingsLite.HeatTransfer.Radiosity.OpaqueSurface bod1(A = 1, absIR = 0.3) "Surface 1" annotation(
    Placement(transformation(origin = {20, -20}, extent = {{-60, 20}, {-40, 40}})));
  BuildingsLite.HeatTransfer.Sources.FixedTemperature T1(T = 293.15) "Boundary condition for surface 1" annotation(
    Placement(transformation(origin = {20, -20}, extent = {{-80, -20}, {-60, 0}})));
  BuildingsLite.HeatTransfer.Radiosity.OpaqueSurface bod2(A = 1, absIR = 0.3) "Surface 2" annotation(
    Placement(transformation(origin = {20, -20}, extent = {{60, 20}, {40, 40}})));
equation
  connect(T1.port, bod1.heatPort) annotation(
    Line(points = {{-40, -30}, {-29.2, -30}, {-29.2, 0.2}}, color = {191, 0, 0}));
  connect(T2.port, bod2.heatPort) annotation(
    Line(points = {{60, -30}, {69.2, -30}, {69.2, 0.2}}, color = {191, 0, 0}));
  connect(bod1.JOut, bod2.JIn) annotation(
    Line(points = {{-19, 14}, {32, 14}, {32, 6}, {59, 6}}, color = {0, 127, 0}));
  connect(bod2.JOut, bod1.JIn) annotation(
    Line(points = {{59, 14}, {36, 14}, {36, 4}, {0, 4}, {0, 6}, {-19, 6}}, color = {0, 127, 0}));
end PartialTestCase;
