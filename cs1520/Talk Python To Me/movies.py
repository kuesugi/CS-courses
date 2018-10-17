class Media:
	def __init__(self, title):
		self.title = title

	def slug(self):
		special_chars = "!#$%&'\"()*+,-./:;<=>?@[]^_`{|}~\\"
		s = ""
		for i in range(0, len(self.title)):
			isSpecial = False
			for j in range(0, len(special_chars)):
				if self.title[i] == special_chars[j]:
					isSpecial = True
			if isSpecial == False:
				s = s + self.title[i]
		# to remove duplicate space
		s = " ".join(s.split())
		# replace space with "-"
		s = s.replace(" ", "-")
		# to lower case
		s = s.lower()
		return s

class Movie(Media):
	def __init__(self, title, year, director, runtime):
		super().__init__(title)
		self.year = year
		self.director = director
		self.runtime = runtime

	def __repr__(self):
		return "<Movie: %s>" % (self.title)

	def __str__(self):
		return "(%d) %s" % (self.year, self.title)

	# TODO: if title is "123"
	def abbreviation(self):
		s = ""
		for x in range(0, len(self.title)):
			if self.title[x].isalpha() or self.title[x].isdigit():
				s = s + self.title[x]
		s = s.lower()
		s = s[0:3]
		return s

def message_dcr(msg):
	def decorator(func):
		def wrapper(*args, **kwargs):
			if msg == "List of Abbreviations":
				print("=====\n%s\n=====" % msg)
			elif msg == "List of Movies Before":
				print("=====\n%s %d\n=====" % (msg, int(args[0])))
			elif msg == "List of Slugs":
				print("=====\n%s\n=====" % msg)
			return func(*args, **kwargs)
		return wrapper
	return decorator

@message_dcr(msg = "List of Movies Before")
def before_year(target_yr):
	try:
		year_list = [movie for movie in movies if movie.year < int(target_yr)]
		
		for year_movie in year_list:
			print(year_movie)
		print()
	except:
		raise NotImplementedError

@message_dcr(msg = "List of Abbreviations")
def abbr():
	try:
		abbr_list = [movie.abbreviation() for movie in movies]
		
		for abbr in abbr_list:
			print(abbr)
		print()
	except:
		raise NotImplementedError

@message_dcr(msg = "List of Slugs")
def slugs():
	try:
		slug_list = [movie.slug() for movie in movies]
		
		for slug in slug_list:
			print(slug)
		print()
	except:
		raise NotImplementedError

def main():
	try:
		print("Thanks for checking the Local Movie Database!")
		slugs()
		abbr()
		# ask for the specified year
		year = input("Please input a specified year -> ")
		before_year(year)
		print("Thank you")
	except:
		raise NotImplementedError

if __name__ == '__main__':
	sw = Movie("S'tar Wars: Episode IV - A New Hope", 1977, "George Lucas", 121.)
	lr = Movie("Lord of the Rings: Fellowship of the Ring", 2001, "Peter Jackson",178.)
	sl = Movie("Schindler's List", 1993, "Steven Spielberg", 195.)
	gbu = Movie("The Good, the Bad, and the Ugly", 1966, "Sergio Leone", 177.)
	et = Movie("E.T. the Extra-Terrestrial", 1982, "Steven Spielberg", 114.)
	am = Movie("12 Angry Men", 1957, "Sidney Lumet", 95.)
	movies = [sw, lr, sl, gbu, et, am]
	main()