/*L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L*/

/* Formatted on 2010/03/31 14:31 (Formatter Plus v4.8.6) */
MERGE INTO sbrext.tool_options_view_ext s
   USING (SELECT 'CDEBrowser' AS tool_name,
                 'PRIVACY_URL' AS property,
                 'https://wiki.nci.nih.gov/x/qxEhAQ'
                                                                     AS VALUE,
                 'caDSR Privacy Policy Statement' AS description
           FROM DUAL) t
   ON (s.tool_name = t.tool_name AND s.property = t.property)
   WHEN MATCHED THEN
      UPDATE
         SET s.VALUE = t.VALUE, s.description = t.description
   WHEN NOT MATCHED THEN
      INSERT (tool_name, property, VALUE, description, locale)
      VALUES (t.tool_name, t.property, t.VALUE, t.description, 'US');
/*
    Commit changes.
*/
COMMIT ;
