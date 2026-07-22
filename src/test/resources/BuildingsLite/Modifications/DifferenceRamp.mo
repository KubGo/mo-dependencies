within BuildingsLite.Modifications;

model DifferenceRamp
  extends BuildingsLite.Modifications.PartialClass(
  redeclare replaceable Modelica.Blocks.Sources.Ramp input1(height = 20, duration = 2, offset = 5, startTime = 2)
    constrainedby Modelica.Blocks.Interfaces.SO,
  redeclare BuildingsLite.Bugfixes.Difference calculation, modifyOutput = true, outputModifier = 2);
  
equation

end DifferenceRamp;