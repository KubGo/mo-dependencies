within BuildingsLite.Modifications;

model DifferenceRamp
  extends BuildingsLite.Modifications.PartialClass(
  redeclare  Modelica.Blocks.Sources.Ramp input1(height = 20, duration = 2, offset = 5, startTime = 2, y(start=5)),
  redeclare BuildingsLite.Bugfixes.Difference calculation, modifyOutput = true, outputModifier = 2);
  
equation

end DifferenceRamp;