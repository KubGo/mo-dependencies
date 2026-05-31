within BuildingsLite.Bugfixes;

partial model PartialCalculation
  Modelica.Blocks.Interfaces.RealInput x annotation(
    Placement(transformation(origin = {-100, 60}, extent = {{-20, -20}, {20, 20}}), iconTransformation(origin = {-98, 64}, extent = {{-20, -20}, {20, 20}})));
  Modelica.Blocks.Interfaces.RealInput y annotation(
    Placement(transformation(origin = {-100, -20}, extent = {{-20, -20}, {20, 20}}), iconTransformation(origin = {-112, -26}, extent = {{-20, -20}, {20, 20}})));
  Modelica.Blocks.Interfaces.RealOutput out annotation(
    Placement(transformation(origin = {110, 10}, extent = {{-10, -10}, {10, 10}}), iconTransformation(origin = {80, 14}, extent = {{-10, -10}, {10, 10}})));
  replaceable function calc = sum
  constrainedby partialFunc
  "Calculation function"
  annotation(choicesAllMatching = true);
  
  equation
  out = calc(x, y);
end PartialCalculation;
