within ModelicaByExample;
model ImportsTest
  import Modelica.Units.SI;
  import Modelica.Blocks.Sources;

  SI.Temperature T;
  SI.Height h;
  Sources.Ramp ramp(duration = 10)  annotation(
    Placement(transformation(origin = {-70, 70}, extent = {{-10, -10}, {10, 10}})));
  Sources.Sine sine(f = 0.5)  annotation(
    Placement(transformation(origin = {-70, 10}, extent = {{-10, -10}, {10, 10}})));
equation
  T = sine.y;
  h = ramp.y;
end ImportsTest;