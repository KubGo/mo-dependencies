within BuildingsLite.Bugfixes;

model Difference
  extends BuildingsLite.Bugfixes.PartialCalculation(redeclare function calc = BuildingsLite.Bugfixes.Functions.difference);
equation

end Difference;
