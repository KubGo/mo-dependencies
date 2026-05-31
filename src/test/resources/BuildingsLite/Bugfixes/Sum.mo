within BuildingsLite.Bugfixes;

model Sum
  extends BuildingsLite.Bugfixes.PartialCalculation(redeclare function calc = BuildingsLite.Bugfixes.sum);
equation

end Sum;
