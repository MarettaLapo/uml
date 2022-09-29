import java.util.*;

public class Main {
	static int MANY_WEEDS = 2000; // Количество растений в пруду
	static double WEED_SIZE = 15; // Начальный размер растения
	static double WEED_RATE = 2.5; // Скорость роста растения
	static int INIT_FISH = 300; // Начальный размер популяции рыб
	static double FISH_SIZE = 50; // Размер рыб
	static double FRACTION = 0.5; // Рыба каждую неделю должна съедать объем равный FRACTION * FISH_SIZE
	static int AVERAGE_NIBBLES = 30; // Среднее количество растений, частично съедаемых рыбами в течение недели равно
	// произведению AVERAGE_NIBBLES на размер популяции рыб
	static double BIRTH_RATE = 0.05; // Общее количество новых рыб равно произведению размера популяции на константу
									 // BIRTH_RATE

	static void pondWeek(ArrayList<Herbivore> fish, ArrayList<Plant> weeds){
	
		int manyIterations = AVERAGE_NIBBLES * fish.size( );
		for (int i = 0; i < manyIterations; ++i){ //выбор питания
			int indexFish = (int) (Math.random() * (fish.size()));
            int indexPlant = (int) (Math.random() * (weeds.size()));
            while(!fish.get(indexFish).isAlive())
                indexFish = (int) (Math.random() * (fish.size()));
            fish.get(indexFish).nibble(weeds.get(indexPlant));
		}

		for(Plant p : weeds){ //растения
			p.simulateWeek();
		}

		for(ListIterator<Herbivore> h = fish.listIterator(); h.hasNext(); ){ //удаление рыб
            Herbivore H = h.next();
            H.simulateWeek();
            if(!H.isAlive())
                h.remove();
        }

		int newFishPopulation = (int)(BIRTH_RATE * fish.size());
		for (int i = 0; i < newFishPopulation; i++){
			fish.add(new Herbivore(0, FISH_SIZE, FISH_SIZE * FRACTION));
		}

	}

	static double totalMass(ArrayList<Plant> weeds) {
		double mass = 0;
        for(Plant p : weeds)
            mass += p.getSize();
        return mass;
	}

	public static void main(String[] args) {
		ArrayList<Plant> weeds = new ArrayList(MANY_WEEDS);
		ArrayList<Herbivore> fish = new ArrayList(INIT_FISH);
	
		Scanner in = new Scanner(System.in);
		int manyWeeks;   // Количество недель для моделирования
		int i;
			
		System.out.println("How many weeks shall I simulate? ");
		manyWeeks=in.nextInt();
			
		//Создание популяции травоядных рыб
		for (i=0;i<INIT_FISH;i++)
			fish.add(new Herbivore (0, FISH_SIZE, FISH_SIZE * FRACTION));
	
		//Создание популяции растений
		for (i=0;i<MANY_WEEDS;i++)
			weeds.add(new Plant(WEED_RATE, WEED_SIZE));

		int fishMxSize = 0;
		double weedsMx = 0;
		ArrayList<Integer> a = new ArrayList<>(manyWeeks);
		ArrayList<Double> b = new ArrayList<>(manyWeeks);
	
		//Моделирование жизни в пруду
		for (i = 0; i < manyWeeks; i++){
			pondWeek(fish, weeds);
            a.add(fish.size());
            b.add(totalMass(weeds));
            fishMxSize = Math.max(fishMxSize, fish.size());
            weedsMx = Math.max(weedsMx, totalMass(weeds));
        }
        // for(i = 0; i < manyWeeks; i++){
        //     System.out.println((double)a.get(i) / (double)fishMxSize + "\t"+ b.get(i) / weedsMx);
        // }
		for (i = 0; i < manyWeeks; i++)
			System.out.println((double)a.get(i) / (double)fishMxSize);
		System.out.println("-------");
		for (i = 0; i < manyWeeks; i++)
			System.out.println(b.get(i) / weedsMx);
	}	
}
