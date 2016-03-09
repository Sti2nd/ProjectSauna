import time
import os
import glob


#Kode som leser temperatur via sensoren paa Raspberry Pi.
os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')

base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

def read_temp_raw():
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()
    return lines

def read_temp():
    lines = read_temp_raw()
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_temp_raw()
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        temp_f = temp_c * 9.0 / 5.0 + 32.0
        return temp_c


#db = MySQLdb.connect(host="mysql.stud.ntnu.no",             # host
#                     user="roberei_hotdog",                 # username
#                     passwd="pekka",                        # password
#                     db="roberei_hotdog_db")                # databasename

#Cursorprosjekt lets you perform queries
#cur = db.cursor()

# SQL & shit here
#temperature = 0.0
#device_on = True
#while(device_on):
#    print(read_temp())
#    temperature = read_temp()
#    cur.execute("INSERT INTO DataLinje (temperatur, raspberry_lopeNr) VALUES ('%s', '%s')",(temperature, 2))
#    time.sleep(5)
#    db.commit()
#db.close()


# PHP server post
import urllib2, urllib
deviceOn = True
while(deviceOn):
    mydata=[('temperatur',read_temp()),('lopeNr','2')]    #The first is the var name the second is the value
    mydata=urllib.urlencode(mydata)
    path='http://folk.ntnu.no/roberei/hotdog/postTemperature.php'    #the url you want to POST to
    req=urllib2.Request(path, mydata)
    req.add_header("Content-type", "application/x-www-form-urlencoded")
    page=urllib2.urlopen(req).read()
    time.sleep(5)
