within BuildingsLite.Modifications;

model SumRamp

  extends DifferenceRamp(
    redeclare BuildingsLite.Bugfixes.Sum calculation);
  
end SumRamp;