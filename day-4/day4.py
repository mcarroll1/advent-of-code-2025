from dataclasses import dataclass

@dataclass
class Point:
    x: int
    y: int

def check_access(point: Point, lines: list[str], max_neighbors: int) -> bool:
    if lines[point.y][point.x] == ".":
        return False
        
    neighbors = 0
    for dy in (-1, 0, 1):
        for dx in (-1, 0, 1):
            if dx == 0 and dy == 0:
                continue
            if point.x + dx < 0 or point.x + dx >= len(lines[point.y]):
                continue
            if point.y + dy < 0 or point.y + dy >= len(lines):
                continue
            if lines[point.y + dy][point.x + dx] == "@":
                neighbors += 1

    return neighbors < max_neighbors

def main():
    with open("input.txt", "r") as file:
        lines = file.readlines()

    paper_limit = 4
    removed = 0
    still_some_accessible = True
    
    while still_some_accessible:
        total_accessible = 0
        still_some_accessible = False
        for y in range(len(lines)):
            for x in range(len(lines[y].strip())):
                point = Point(x, y)
                accessible = check_access(point, lines, paper_limit)
                if accessible:
                    still_some_accessible = True
                    total_accessible += 1
                    removed += 1
                    print(f"Paper accessible at ({point.x}, {point.y}), removing")
                    lines[y] = lines[y][:x] + "." + lines[y][x+1:]

        print(f"Total accessible papers: {total_accessible}")
        print(f"Total removed {removed}")


if __name__ == "__main__":
    main()
