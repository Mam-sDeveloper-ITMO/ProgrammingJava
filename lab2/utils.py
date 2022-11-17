import os


def count_lines() -> int:
    count = 0
    for dirpath, dirnames, filenames in os.walk('.'):
        for filename in filenames:
            if filename.endswith('.java'):
                path = os.path.join(dirpath, filename)
                with open(path) as f:
                    lines = f.readlines()
                    lines = [line for line in lines if line.strip()]
                    lines = [line for line in lines if not 'import' in line]
                    lines = [line for line in lines if not 'package' in line]
                    _count = len(lines)
                    print(path, _count)
                    count += _count

    return count


def parse_enum(text: str, sep=' ') -> str:
    text = text.replace('-', '_')
    words = text.split(sep)
    words = [word.strip().upper() for word in words if word]
    return ',\n'.join(set(words))


if __name__ == '__main__':
    print(count_lines())
