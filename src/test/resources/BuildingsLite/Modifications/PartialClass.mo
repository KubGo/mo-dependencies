within BuildingsLite.Modifications;

partial model PartialClass

  parameter Boolean modifyOutput = false
    "If true, modify output";
  parameter Real outputModifier = 1
    "Output modifier"
    annotation(enable = modifyOutput);
  
  replaceable Modelica.Blocks.Sources.Constant input1(k = 10)
  constrainedby Modelica.Blocks.Interfaces.SO
   annotation(
    Placement(transformation(origin = {-90, 30}, extent = {{-10, -10}, {10, 10}})));
  replaceable Modelica.Blocks.Sources.Constant input2(k = 20)  
  constrainedby Modelica.Blocks.Interfaces.SO
  annotation(
    Placement(transformation(origin = {-90, -50}, extent = {{-10, -10}, {10, 10}})));
  replaceable BuildingsLite.Bugfixes.Sum calculation
  constrainedby Bugfixes.PartialCalculation annotation(
    Placement(transformation(extent = {{-20, -20}, {20, 20}})));
  Modelica.Blocks.Sources.RealExpression outputCalculation(y = if modifyOutput then outputModifier * calculation.out else calculation.out)  annotation(
    Placement(transformation(origin = {58, 0}, extent = {{-10, -10}, {10, 10}})));
  Modelica.Blocks.Interfaces.RealOutput out annotation(
    Placement(transformation(origin = {110, 0}, extent = {{-10, -10}, {10, 10}}), iconTransformation(origin = {100, 0}, extent = {{-10, -10}, {10, 10}})));
equation
  connect(input1.y, calculation.x) annotation(
    Line(points = {{-78, 30}, {-20, 30}, {-20, 12}}, color = {0, 0, 127}));
  connect(input2.y, calculation.y) annotation(
    Line(points = {{-78, -50}, {-22, -50}, {-22, -6}}, color = {0, 0, 127}));
  connect(outputCalculation.y, out) annotation(
    Line(points = {{70, 0}, {110, 0}}, color = {0, 0, 127}));
end PartialClass;