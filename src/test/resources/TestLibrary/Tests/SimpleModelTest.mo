within TestLibrary.Tests;

model SimpleModelTest
  BuildingsLite.Tests.SimpleModel simpleModel annotation(
    Placement(transformation(origin = {10, 10}, extent = {{-10, -10}, {10, 10}})));
  BuildingsLite.Controls.Sources.DayType dayType annotation(
    Placement(transformation(origin = {-50, 50}, extent = {{-10, -10}, {10, 10}})));
equation

end SimpleModelTest;
