within BuildingsLite.Bugfixes;

model Sum
  extends BuildingsLite.Bugfixes.PartialCalculation(redeclare function calc = BuildingsLite.Bugfixes.Functions.sum);
equation

end Sum;
