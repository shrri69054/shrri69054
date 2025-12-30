import datetime

ONE_DATE = datetime.timedelta(days=1)
QUARTERS = [(0, 4), (0, 7), (0, 10), (1, 1)]


def delivery_date(start, description):
    start_date = datetime.datetime.fromisoformat(start)
    if description == "NOW":
        # NOW: Two hours after the meeting started
        delivery = start_date + datetime.timedelta(seconds=60*60*2)
    elif description == "ASAP":
        # ASAP Before 12:00 => Today at 17:00
        # ASAP After  12:00 => Tomorrow at 12:00
        if start_date.time() < datetime.time(13):
            delivery = start_date.replace(hour=17, minute=0, second=0)
        else:
            delivery = start_date.replace(hour=13, minute=0, second=0) + ONE_DATE
    elif description == "EOW":
        # EOW Monday, Tuesday, Wednesday => Friday at 17:00
        # EOW Thursday or Friday         => Sunday at 20:00
        if start_date.weekday() < 3:  # Mon, Tue, Wed
            delivery = start_date.replace(hour=17, minute=0, second=0) + datetime.timedelta(days=4 - start_date.weekday())
        else:
            delivery = start_date.replace(hour=20, minute=0, second=0) + datetime.timedelta(days=6 - start_date.weekday())
    elif description.endswith("M"):
        # "<N>M" Before N-th month      => At 8:00 on the first workday¹ of this year's N-th month
        # "<N>M" After or in N-th month => At 8:00 on the first workday¹ of next year's N-th month
        month = int(description.removesuffix("M"))
        delivery = start_date.replace(month=month, day=1, hour=8, minute=0, second=0)
        if start_date.month >= month:
            delivery = delivery.replace(year=delivery.year + 1)
        while delivery.weekday() >= 5:  # weekend
            delivery += ONE_DATE
    elif description.startswith("Q"):
        # "Q<N>" Before or in N-th quarter² => At 8:00 on the last workday¹ of this year's N-th quarter²
        # "Q<N>" After N-th quarter²        => At 8:00 on the last workday¹ of next year's N-th quarter²
        quarter = int(description.removeprefix("Q"))
        current = ((start_date.month - 1) // 3) + 1
        year_offset, month = QUARTERS[quarter - 1]
        if current > quarter:
            year_offset = 1
        delivery = start_date.replace(year=start_date.year + year_offset, month=month, day=1, hour=8, minute=0, second=0)
        delivery -= ONE_DATE
        while delivery.weekday() >= 5:  # weekend
            delivery -= ONE_DATE

    return delivery.isoformat()