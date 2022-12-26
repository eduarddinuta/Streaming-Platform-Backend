Dinuta Eduard-Stefan 321CA
Tema 2 POO - Etapa 1

   Tema isi propune implementarea backend-ului unei platforme specifice
vizualizarii de filme si seriale folosind conceptele OOP. Implementarea
etapei 1 foloseste 3 design pattern-uri: singleton, visitor si factory.
   Clasa main este clasa de baza de la care porneste executia fiecarui test.
Sunt create obiecte File pentru fisierele de intrare si iesire si este citit
inputul din fisierul json. Este apelata functia startPage care incepe executia
unei pagini folosind instanta singleton a clase PlatformGenerator.
   Clasa PlatformGenerator este clasa de initiala a platformei, implementata
folosind pattern-ul singleton deoarece este de ajuns sa avem o singura pagina
mereu. Aici sunt construite array-urile de useri, filme si actiuni, cat si 
HashMap-urile care contin actiunile permise pe fiecare pagina si paginile
la care se poate ajunge direct de la fiecare pagina. Astfel pe viitor se vor
putea adauga usor la cheia fiecarei pagini noi actiuni permise sau pagini la
care se poate ajunge. Tot in platform generator este retinuta o instanta a 
paginii curente, caracterizata prin nume, fiind initializata cu 
"homepage neautentificat" si intrarile corespunzatoare din HashMap-uri.
  Dupa ce au fost facute toate initializarile se itereaza prin lista de
actiuni si se construieste obiectul din clasa corespunzatoare fiecarei
actiuni folosind design pattern-ul factory. Clasa ActionFactory, de asemenea
singleton, returneaza prin functia create Action o actiune in functie de tipul
acesteia (change page sau on page) si de feature, dand o exceptie daca acestea
nu sunt cunoscute, cu posibilitatea de extindere la noi tipuri de actiuni
foarte usor.
  Implementare actiunilor este realizata folosind pattern-ul visitor. Pentru
actiuni este definita clasa abstracta ActionVisitor care contine functia visit
ce va fii implementata de fiecare actiune si numele actiunii. Fiecare actiune
are propria clasa in care este implementata functia visit. Schimbarea paginii
pe "See Details" are o actiune separata de actinuea obisnuita de change page
(ChangePageAction) deoarece am considerat ca este un caz mai special care ar
trebui tratat separat. De asemenea actiunea de filter a fost implementata
cat mai generic pentru a putea adauga viitoare criterii. Pentru feature-ul
contains este folosita functia checkContains care verifica daca un film
contine toate elementele dorite, iar sortarea este realizata cu o expresie
lambda la care pot fii adaugate usor alte criterii.
   Ca elemente vizitate vom avea pagini. Pentru asta exista interfata Page,
care contine functia de accept, ce primeste un vizitator actiune. Interfata
Page este implementata doar de clasa ConcretePage pentru ca, cel putin pentru
etapa 1, am considerat ca nu exista diferente majore intre pagini pentru a 
justifica crearea unei clase pentru fiecare, ceea ce ar fi dus la multe functii
goale in clasele visitor (insa noi pagini pot fii adaugate cu usurinta in
viitor la nevoie, la fel ca si noi actiuni). Clasa ConcretePage implementeaza
functia accept, care apeleaza functia visit specifica fiecarei actiuni, fara
a ne mai face griji de tipul acesteia.
  De asemenea afisarea outputului (si a erorilor) este realizata de clasa
Output care contine campurile error, currentMovieList, currentUser,
initializate in functie de nevoie pentru fiecare tip de output.
   