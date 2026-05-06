within BuildingsLite.Tests;

model MultipleExtendsModel

  extends SimpleModel;
  extends HeatTransfer.Conduction.DummyModel;

end MultipleExtendsModel;