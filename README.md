# Klasa FileCabinet

Klasa `FileCabinet` implementuje interfejs `Cabinet` i zapewnia funkcjonalność zarządzania kolekcją folderów. Obsługuje wyszukiwanie folderów według nazwy, znajdowanie folderów według rozmiaru oraz liczenie łącznej liczby folderów, w tym tych zagnieżdżonych w instancjach `MultiFolder`.

## Pola

- **folders**: Prywatne pole `List<Folder>`, które przechowuje kolekcję folderów zarządzanych przez `FileCabinet`.

## Konstruktor

- **`FileCabinet(List<Folder> folders)`**: Inicjalizuje nową instancję `FileCabinet` z dostarczoną listą folderów.

## Metody publiczne

- **`Optional<Folder> findFolderByName(final String name)`**:
  - Wyszukuje pierwszy folder, którego nazwa odpowiada podanej wartości `name`.
  - Używa metody `findFirstMatching` do wykonania wyszukiwania.
  - Zwraca `Optional<Folder>`, który może być pusty, jeśli nie znaleziono pasującego folderu.

- **`List<Folder> findFoldersBySize(final String size)`**:
  - Znajduje i zwraca listę folderów o określonym rozmiarze `size`.
  - Używa metody `findAllMatching` do filtrowania i zbierania pasujących folderów do listy wynikowej (`resultList`).

- **`int count()`**:
  - Zlicza łączną liczbę folderów.
  - Używa metody `findAllMatching` z predykatem zawsze zwracającym `true`, aby policzyć wszystkie foldery.

## Metody prywatne

- **`Optional<Folder> findFirstMatching(Predicate<Folder> condition, List<Folder> folders)`**:
  - Rekurencyjnie przeszukuje listę folderów, aby znaleźć pierwszy folder, który spełnia podany `condition`.
  - Jeśli folder jest instancją `MultiFolder`, przeszukuje również foldery wewnętrzne.

- **`void findAllMatching(Predicate<Folder> condition, List<Folder> folders, Consumer<Folder> action)`**:
  - Rekurencyjnie przeszukuje listę folderów i wykonuje określoną `action` na każdym folderze, który spełnia podany `condition`.
  - Jeśli folder jest instancją `MultiFolder`, przeszukuje również foldery wewnętrzne.

## Założenia

- **Folder**: `Folder` jest interfejsem, który reprezentuje pojedynczy folder. Konieczne jest użycie klas implementujących ten interfejs, takich jak `SimpleFolder`.

- **MultiFolder**: `MultiFolder` jest interfejsem rozszerzającym `Folder`, który reprezentuje kontener folderów. Konieczne jest użycie klas implementujących ten interfejs, takich jak `CompositeFolder`.

## Testy jednostkowe

Testy jednostkowe dla klasy `FileCabinet` zostały zaimplementowane przy użyciu frameworka JUnit 5. Poniżej znajduje się opis każdego testu zawartego w klasie `FileCabinetTest`. Każdy test używa kilku folderów z różnymi nazwami i rozmiarami, w tym folderów zagnieżdżonych, aby upewnić się, że metody klasy `FileCabinet` działają zgodnie z oczekiwaniami w różnych scenariuszach. Testy obejmują przypadki zarówno dla folderów głównych, jak i zagnieżdżonych, oraz różne rozmiary folderów.

### Testy

- **`testFindFolderByName_Found()`**
  - **Opis**: Sprawdza, czy metoda `findFolderByName` poprawnie znajduje folder o nazwie "Music". 
  - **Oczekiwanie**: Test powinien potwierdzić, że folder o nazwie "Music" istnieje w kolekcji folderów i jest prawidłowo zwrócony.

- **`testFindFolderByName_NotFound()`**
  - **Opis**: Sprawdza, czy metoda `findFolderByName` poprawnie obsługuje sytuację, gdy folder o nazwie "Videos" nie istnieje.
  - **Oczekiwanie**: Test powinien potwierdzić, że nie znaleziono folderu o nazwie "Videos", co powinno skutkować pustym wynikiem.

- **`testFindFolderByName_FoundInNestedFolder()`**
  - **Opis**: Sprawdza, czy metoda `findFolderByName` poprawnie znajduje folder o nazwie "Work", który znajduje się wewnątrz folderu zagnieżdżonego (`CompositeFolder`).
  - **Oczekiwanie**: Test powinien potwierdzić, że folder o nazwie "Work" jest obecny w folderze zagnieżdżonym i został prawidłowo zwrócony.

- **`testFindFoldersBySize_Small()`**
  - **Opis**: Sprawdza, czy metoda `findFoldersBySize` poprawnie zwraca foldery o rozmiarze "SMALL". 
  - **Oczekiwanie**: Test powinien potwierdzić, że wynikiem jest lista zawierająca foldery "Documents" i "Work", które są odpowiednie dla rozmiaru "SMALL".

- **`testFindFoldersBySize_Medium()`**
  - **Opis**: Sprawdza, czy metoda `findFoldersBySize` poprawnie zwraca foldery o rozmiarze "MEDIUM".
  - **Oczekiwanie**: Test powinien potwierdzić, że wynikiem jest lista zawierająca foldery "Music" i "Personal", które są odpowiednie dla rozmiaru "MEDIUM".

- **`testFindFoldersBySize_Large()`**
  - **Opis**: Sprawdza, czy metoda `findFoldersBySize` poprawnie zwraca foldery o rozmiarze "LARGE".
  - **Oczekiwanie**: Test powinien potwierdzić, że wynikiem jest lista zawierająca foldery "Pictures" i "Archives", które są odpowiednie dla rozmiaru "LARGE".

- **`testCount()`**
  - **Opis**: Sprawdza, czy metoda `count` poprawnie liczy łączną liczbę folderów w `FileCabinet`.
  - **Oczekiwanie**: Test powinien potwierdzić, że metoda `count` zwraca poprawną liczbę folderów, która w tym przypadku wynosi 6 - 4 foldery główne(w tym jeden `MultiFolder` + 2 zagnieżdżone).


