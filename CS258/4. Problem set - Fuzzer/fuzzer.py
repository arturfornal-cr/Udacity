import math
import random
import string
import subprocess
import time
import os

location = "files"
app = "C:\\Program Files\\SciTE\\SciTE.exe"
file_extension = [".png",".ico",".txt",".java"]
fuzz_output = ""
FuzzFactor = 250
num_tests = 150
num_crashed = 0 


def random_file():
    ''' Random file generator '''
    extension = random.choice(file_extension)
    global fuzz_output
    fuzz_output="fuzz"+extension
    rfile = "file"+str(random.randint(1,5))+extension
    return os.path.join(os.getcwd(),location,rfile)


start_time = time.time() 
for i in range(num_tests):    
    file_choice = random_file()
    buf = bytearray(open(file_choice,'rb').read())    
    numwrites = random.randrange(math.ceil((float(len(buf))/FuzzFactor)))+ 100
    
    for j in range(numwrites):
        rbyte = random.randrange(256)
        rn = random.randrange(len(buf))
        buf[rn] = "%c"%(rbyte)
        
    t = open(fuzz_output, 'wb')
    t.write(buf)
    process = subprocess.Popen([app,fuzz_output])
    
    time.sleep(0.5)
    crashed = process.poll()
    if crashed :
        num_crashed+=1
        print "Number of crashes:",num_crashed
        print "number of writes:", numwrites
        print "return code:", process.returncode
        print 
    else: 
        process.terminate()
        
end_time = (time.time()-start_time)         
print "Number of crashes is",num_crashed  
print "Time taken to complete %d iterations is %f s" % (num_tests, end_time)