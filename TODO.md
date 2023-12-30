v Składa się ze zwykłej, prostokątnej połaci podzielonej na kwadratowe pola.
 Większość świata pokrywają stepy, na których rośnie niewiele roślin stanowiących pożywienie zwierzaków.
 Niektóre rejeony porasta jednak dżungla, gdzie rośliny rosną dużo szybciej.
 Rośliny będą wyrastały w losowych miejscach, ale ich koncentracja będzie większa w dżungli niż na stepie.
 
v Nasze zwierzęta, które są roślinożercami, będą przemierzały ten świat w poszukiwaniu pożywienia.
v Każdy zwierzak ma określoną energię,
 która zmniejsza się co dnia.
 Znalezienie i zjedzenie rośliny zwiększa poziom energii o pewną wartość.
 
v Po pierwsze, zarówno w przypadku rośliny jak i tych, którzy je zjadają, musimy znać koordynaty `x` i `y`.
v Musimy także wiedzieć, ile energii ma dany zwierzak.
 jeśli zwierzak nie zdoła zdobyć odpowiedniej ilości pożywienia, będzie głodować i zdechnie...
? Energia mówi nam o tym, ile dni funkcjonowania zostało jeszcze danemu zwierzakowi.
 
 Istnieje osiem różnych możliwych pozycji i tyle samo możliwych obrotów.
v Musimy również pamiętać, w którą stronę zwierzak jest zwrócony.
 Obrót `0` oznacza, że zwierzak nie zmienia swojej orientacji, obrót `1` oznacza, że zwierzak obraca się o 45°, `2`, o 90°, itd.
 
v? Na koniec musimy także przechowywać geny zwierzaka.
v? Każdy zwierzak ma N genów, z których każdy jest jedną liczbą z zakresu od `0` do `7`.
 Geny te opisują (w bardzo uproszczony sposób) schemat zachowania danej istoty.
 Egzystencja naszych zwierzaków ma cykliczną naturę.
 Każdy z nich przechowuje informację o tym, z którego fragmentu swojego genomu będzie korzystał najbliższego dnia.
 Podczas każdego ruchu zwierzak zmienia najpierw swoje ustawienie, obracając się zgodnie z aktualnie aktywnym genem, a potem porusza się o jedno pole w wyznaczonym kierunku.
 Następnie gen ulega dezaktywacji, a aktywuje się gen na prawo od niego (będzie sterował zwierzakiem kolejnego dnia).
 Gdy geny skończą się, to aktywacja wraca na początek ich listy.
 
 Zakładamy, że zwierzak zjada roślinę, gdy stanie na jej polu,
 a jego energia wzrasta wtedy o
v z góry zdefiniowaną wartość.
 Zdrowe młode może mieć tylko zdrowa para rodziców, dlatego nasze zwierzaki będą się rozmnażać tylko jeśli mają odpowiednią ilość energii.
 Przy reprodukcji rodzice tracą na rzecz młodego pewną część swojej energii - ta energia będzie rónocześnie stanowić startową energię ich potomka.
 
 Urodzone zwierzę otrzymuje genotyp będący krzyżówką genotypów rodziców.
 Udział genów jest proporcjonalny do energii rodziców i wyznacza miejsce podziału genotypu.
 Udział ten określa miejsce przecięcia genotypu, przyjmując, że geny są uporządkowane.
 W pierwszym kroku losowana jest strona genotypu, z której zostanie wzięta część osobnika silniejszego, np. *prawa*.
 W tym przypadku dziecko otrzymałoby odcinek obejmujący 25% *lewych* genów pierwszego rodzica oraz 75% *prawych* genów drugiego rodzica.
 Jeśli jednak wylosowana byłaby strona *lewa*, to dziecko otrzymałoby 75% *lewych* genów silniejszego osobnika oraz 25% *prawych* genów.
 Na koniec mają zaś miejsce mutacje: losowa liczba (wybranych również losowo) genów potomka zmienia swoje wartości na zupełnie nowe.
 
 Symulacja każdego dnia składa się z poniższej sekwencji kroków:
 1. Usunięcie martwych zwierzaków z mapy.
 2. Skręt i przemieszczenie każdego zwierzaka.
 3. Konsumpcja roślin, na których pola weszły zwierzaki.
 4. Rozmnażanie się najedzonych zwierzaków znajdujących się na tym samym polu.
 5. Wzrastanie nowych roślin na wybranych polach mapy.
 
 Daną symulację opisuje szereg parametrów:
v * wysokość i szerokość mapy,
? * wariant mapy (wyjaśnione w sekcji poniżej),
v * startowa liczba roślin,
v * energia zapewniana przez zjedzenie jednej rośliny,
v * liczba roślin wyrastająca każdego dnia,
? * wariant wzrostu roślin (wyjaśnione w sekcji poniżej),
v * startowa liczba zwierzaków,
v * startowa energia zwierzaków,
v * energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania),
v * energia rodziców zużywana by stworzyć potomka,
v * minimalna i maksymalna liczba mutacji u potomków (może być równa `0`),
? * wariant mutacji (wyjaśnione w sekcji poniżej),
v * długość genomu zwierzaków,
? * wariant zachowania zwierzaków (wyjaśnione w sekcji poniżej).
 
 * [obowiązkowo dla wszystkich] **kula ziemska** - lewa i prawa krawędź mapy zapętlają się (jeżeli zwierzak wyjdzie za lewą krawędź, to pojawi się po prawej stronie - a jeżeli za prawą, to po lewej);
     górna i dolna krawędź mapy to bieguny - nie można tam wejść (jeżeli zwierzak próbuje wyjść poza te krawędzie mapy, to pozostaje na polu na którym był, a jego kierunek zmienia się na odwrotny);
 * [A] **piekielny portal** - jeżeli zwierzak wyjdzie poza krawędź mapy, to trafia do magicznego portalu;
 jego energia zmniejsza się o pewną wartość (taką samą jak w przypadku generacji potomka),
 a następnie jest teleportowany w nowe, losowe wolne miejsce na mapie;

 * [obowiązkowo dla wszystkich] **zalesione równiki** - preferowany przez rośliny jest poziomy pas pól w centralnej części mapy (udający równik i okolice);
 
v? * [obowiązkowo dla wszystkich] **pełna losowość** - mutacja zmienia gen na dowolny inny gen;
v? * [2] **podmianka** - mutacja może też skutkować tym, że dwa geny zamienią się miejscami.

 * [obowiązkowo dla wszystkich] **pełna predestynacja** - zwierzak zawsze wykonuje kolejno geny, jeden po drugim;
 
 1. Aplikacja ma być realizowana z użyciem graficznego interfejsu użytkownika z wykorzystaniem biblioteki JavaFX.
 2. Jej głównym zadaniem jest umożliwienie uruchamiania symulacji o wybranych konfiguracjach.
    1. Powinna umożliwić wybranie jednej z uprzednio przygotowanych gotowych konfiguracji,
    1. "wyklikanie" nowej konfiguracji,
    1. oraz zapisanie jej do ponownego użytku w przyszłości.
 3. Uruchomienie symulacji powinno skutkować pojawieniem się nowego okna obsługującego daną symulację.
    1. Jednocześnie uruchomionych może być wiele symulacji, każda w swoim oknie, każda na osobnej mapie.
 4. Sekcja symulacji ma wyświetlać animację pokazującą pozycje zwierzaków, ich energię w dowolnej formie (np. koloru lub paska zdrowia) oraz pozycje roślin - i ich zmiany.
 5. Program musi umożliwiać zatrzymywanie oraz wznawianie animacji w dowolnym momencie (niezależnie dla każdej mapy - patrz niżej).
 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
    * liczby wszystkich zwierzaków,
    * liczby wszystkich roślin,
    * liczby wolnych pól,
    * najpopularniejszych genotypów,
    * średniego poziomu energii dla żyjących zwierzaków,
    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
 7. Po zatrzymaniu programu można oznaczyć jednego zwierzaka jako wybranego do śledzenia. Od tego momentu (do zatrzymania śledzenia) UI powinien przekazywać nam informacje o jego statusie i historii:
    * jaki ma genom,
    * która jego część jest aktywowana,
    * ile ma energii,
    * ile zjadł roślin,
    * ile posiada dzieci,
    * ile posiada potomków (niekoniecznie będących bezpośrednio dziećmi),
    * ile dni już żyje (jeżeli żyje),
    * którego dnia zmarło (jeżeli żywot już skończyło).
 8. Po zatrzymaniu programu powinno być też możliwe:
    * pokazanie, które ze zwierząt mają dominujący (najpopularniejszy) genotyp (np. poprzez wyróżnienie ich wizualnie),
    * pokazanie, które z pól są preferowane przez rośliny (np. poprzez wyróżnienie ich wizualnie).
 9. Jeżeli zdecydowano się na to w momencie uruchamiania symulacji, to jej statystyki powinny być zapisywane (każdego dnia) do pliku CSV. Plik ten powinnien być "otwieralny" przez dowolny rozujmiejący ten format program (np. MS Excel). 
 10. Aplikacja powinna być możliwa do zbudowania i uruchomienia z wykorzystaniem Gradle'a.
 
 * Nowo narodzony (lub wygenerowany) zwierzak jest ustawiony w losowym kierunku. Ma też aktywowany losowy gen (niekoniecznie pierwszy).
 * Narodzone dziecko pojawia się na tym samym polu co jego rodzice.
 * UI nie musi pozwalać na wprowadzanie dowolnych wartości parametrów. Lepiej ograniczyć dopuszczalne zakresy (w szczególności do takich, które nie spowodują natychmiastowego zawieszenia aplikacji).
 * Energię traktujemy całkowitoliczbowo. Pilnujemy jednak, by jej jedynym źródłem były rośliny (po rozmnażaniu się suma energii organizmów na danym polu powinna być taka sama jak przed rozmnażaniem).
 * Jeżeli na jednym polu kilka zwierzaków rywalizuje o roślinę (albo o możliwość rozmnażania), to konflikt ten jest rozwiązywany w następujący sposób:
   - pierwszeństwo mają organizmy o największej energii,
   - jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy najstarsze,
   - jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy o największej liczbie dzieci,
   - jeżeli to nie pozwala rozstrzygnąć, to wśród remisujących organizmów wybieramy losowo.
 * Rośliny mogą rosnąć tam, gdzie stoją zwierzaki. Zjadanie ma miejsce w momencie wchodzenia na pole. Potem zwierzak nie przeszkadza już w istnieniu rośliny.
 * Nowe rośliny nie pojawiają się, jeżeli nie ma już dla nich miejsca na mapie.
 * Statystyki nie muszą być prezentowane w formie wykresu (choć na pewno byłoby to ciekawe usprawnienie).
 * Powyższa specyfikacja może różnić się trochę (lub bardzo) od analogicznych dokumentów znanych ubiegłym rocznikom. Zaleca się czujność i unikanie dróg na skróty. :)
