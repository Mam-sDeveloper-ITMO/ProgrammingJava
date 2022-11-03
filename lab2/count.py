import os


count = 0
for dirpath, dirnames, filenames in os.walk('.'):
    for filename in filenames:
        if filename.endswith('.java'):
            path = os.path.join(dirpath, filename)
            with open(path) as f:
                _count = len(f.readlines())
                print(path, _count)
                count += _count

print(count)