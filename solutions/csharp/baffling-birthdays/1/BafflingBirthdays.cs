public static class BafflingBirthdays
{
	private static Random _rand = new Random();
	
    public static DateOnly[] RandomBirthdates(int numberOfBirthdays) =>
		Enumerable.Range(0, numberOfBirthdays)
		.Select(x => 
				{
					var y = 0;
					do
					{
						y = _rand.Next(1900, DateTime.Now.Year);
					} while (DateTime.IsLeapYear(y));
					return new DateOnly(y, 1, 1).AddDays(_rand.Next(0, 365));
				}).ToArray();

    public static bool SharedBirthday(DateOnly[] birthdays) =>
		birthdays.GroupBy(x => (Month: x.Month, Day: x.Day)).Any(g => g.Count() > 1);
    
    public static double EstimatedProbabilityOfSharedBirthday(int numberOfBirthdays)
    {
		var noShared = 100.0;
		for(var i = 0; i < numberOfBirthdays; i++)
			noShared *= ((365.0 - i) / 365.0);
		return 100.0 - noShared;
    }
}