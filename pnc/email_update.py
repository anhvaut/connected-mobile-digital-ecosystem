#!/usr/bin/env python
#

import smtplib
import subprocess,sys
import os, socket
from email.MIMEMultipart import MIMEMultipart
from email.MIMEBase import MIMEBase
from email.MIMEText import MIMEText
from email import Encoders

hostname = socket.gethostname()
gmail_user = hostname+"@gmail.com"
gmail_pwd = "hollyzhu"

def mail(to, subject, text, attach):
   msg = MIMEMultipart()

   msg['From'] = gmail_user
   msg['To'] = to
   msg['Subject'] = subject

   msg.attach(MIMEText(text))

   if attach:
       part = MIMEBase('application', 'octet-stream')
       part.set_payload(open(attach, 'rb').read())
       Encoders.encode_base64(part)
       part.add_header('Content-Disposition',
               'attachment; filename="%s"' % os.path.basename(attach))
       msg.attach(part)

   mailServer = smtplib.SMTP("smtp.gmail.com", 587)
   mailServer.ehlo()
   mailServer.starttls()
   mailServer.ehlo()
   mailServer.login(gmail_user, gmail_pwd)
   mailServer.sendmail(gmail_user, to, msg.as_string())
   # Should be mailServer.quit(), but that crashes...
   mailServer.close()

tailOutput = ""
attachment = ""
if (len(sys.argv) >1):
   attachment = sys.argv[1]
else:
#tailOutput = subprocess.check_output(['/usr/bin/tail', '-20', '/Users/edou/Documents/unishuttle01_data/checkpassenger.log'], stderr=subprocess.STDOUT)
# Change for on board to /var/log
   tailOutput="output from /var/log/check_watchdog\n"
   tailOutput= subprocess.check_output(['/usr/bin/tail', '-5', '/var/log/check_watchdog'],stderr=subprocess.STDOUT)
   tailOutput=tailOutput+"\noutput from /var/log/checkpassenger.log\n"
   tailOutput = tailOutput+subprocess.check_output(['/usr/bin/tail', '-75', '/var/log/checkpassenger.log'], stderr=subprocess.STDOUT)

   
mail(gmail_user,
   "Hourly update from "+hostname, tailOutput,attachment)
